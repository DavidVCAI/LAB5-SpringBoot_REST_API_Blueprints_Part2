# ANÁLISIS DE CONCURRENCIA - BLUEPRINTS REST API

## IDENTIFICACIÓN DE PROBLEMAS DE CONCURRENCIA

### 1. CONDICIONES DE CARRERA IDENTIFICADAS

#### Problema 1: HashMap no Thread-Safe

- **Ubicación**: InMemoryBlueprintPersistence.java
- **Descripción**: HashMap no es thread-safe. Accesos concurrentes pueden causar:
  - Corrupción de datos internos
  - Bucles infinitos en operaciones de lectura
  - Pérdida de datos durante redimensionamiento
- **Impacto**: Fallas catastróficas del sistema en entorno multiusuario

#### Problema 2: Operación Check-Then-Act No Atómica
 
- **Ubicación**: saveBlueprint() método original
- **Descripción**: La secuencia "verificar si existe → insertar si no existe" no es atómica
- **Escenario de falla**:

```
Hilo A: containsKey(key) → false
Hilo B: containsKey(key) → false  
Hilo A: put(key, blueprint)
Hilo B: put(key, blueprint) → SOBRESCRIBE sin excepción
```

- **Resultado**: Blueprints duplicados insertados sin detección

### 2. REGIONES CRÍTICAS IDENTIFICADAS

#### Región Crítica 1: Método saveBlueprint()

- **Recursos compartidos**: Map blueprints
- **Operaciones**: Verificación de existencia + inserción condicional
- **Concurrencia**: Múltiples threads pueden ejecutar simultáneamente

#### Región Crítica 2: Método updateBlueprint()

- **Recursos compartidos**: Map blueprints  
- **Operaciones**: Verificación de existencia + actualización
- **Riesgo**: Similar check-then-act, pero menor impacto

#### Región Crítica 3: Operaciones de Lectura Durante Modificación

- **Métodos afectados**: getBlueprint(), getAllBlueprints(), getBlueprintsByAuthor()
- **Riesgo**: Lecturas inconsistentes durante modificaciones concurrentes

## SOLUCIONES IMPLEMENTADAS

### Solución 1: ConcurrentHashMap

- **Cambio**: HashMap → ConcurrentHashMap
- **Beneficio**: Thread-safety automático para operaciones básicas
- **Rendimiento**: Alta concurrencia de lectura, escritura segmentada

### Solución 2: Operación Atómica putIfAbsent()
- 
- **Implementación**:

```java
Blueprint existing = blueprints.putIfAbsent(key, blueprint);
if (existing != null) {
    throw new BlueprintPersistenceException(...);
}
```
  
- **Beneficio**: Operación atómica que garantiza inserción única
- **Rendimiento**: Sin bloqueo global, solo sincronización localizada

### Solución 3: Operaciones de Solo Lectura Seguras

- **Métodos**: get(), values(), iteration
- **Garantía**: ConcurrentHashMap proporciona vistas consistentes
- **Beneficio**: Lecturas sin bloqueo, alta escalabilidad

## ANÁLISIS DE RENDIMIENTO

### Estrategia Rechazada: Sincronización Global

```java
// MAL - Degrada rendimiento significativamente
public synchronized void saveBlueprint(Blueprint blueprint) {
    // toda la lógica sincronizada
}
```

**Problemas**:

- Serializa TODOS los accesos (lectura y escritura)
- Elimina beneficios de concurrencia
- Cuello de botella en alta concurrencia

### Estrategia Implementada: Sincronización Granular

```java
// BIEN - Alto rendimiento
Blueprint existing = blueprints.putIfAbsent(key, blueprint);
```
**Beneficios**:

- Sincronización solo cuando necesaria
- Lecturas concurrentes sin bloqueo
- Escalabilidad horizontal

## VERIFICACIÓN DE THREAD-SAFETY

### Operaciones Seguras Garantizadas:

1. **saveBlueprint()**: Operación atómica putIfAbsent()
2. **getBlueprint()**: Lectura thread-safe con ConcurrentHashMap
3. **getAllBlueprints()**: Iteración segura con snapshot
4. **getBlueprintsByAuthor()**: Filtrado thread-safe
5. **updateBlueprint()**: put() atómico en ConcurrentHashMap

### Propiedades de Consistencia:

- **Atomicidad**: Cada operación individual es atómica
- **Visibilidad**: Cambios visibles inmediatamente entre threads
- **Ordenamiento**: Operaciones respetan happens-before relationships

## TESTING DE CONCURRENCIA RECOMENDADO

```java
// Test de carga concurrente
@Test
public void testConcurrentBlueprintCreation() {
    ExecutorService executor = Executors.newFixedThreadPool(10);
    CountDownLatch latch = new CountDownLatch(100);
    
    for (int i = 0; i < 100; i++) {
        executor.submit(() -> {
            try {
                // Intentar crear blueprints concurrentemente
                persistence.saveBlueprint(new Blueprint("author", "name", points));
            } catch (BlueprintPersistenceException e) {
                // Esperado para duplicados
            } finally {
                latch.countDown();
            }
        });
    }
    
    latch.await();
    // Verificar que solo se creó un blueprint
    assertEquals(1, persistence.getAllBlueprints().size());
}
```

## CONCLUSIONES

La solución implementada garantiza:

1. **Thread-Safety Completa**: Sin condiciones de carrera
2. **Alto Rendimiento**: Lecturas concurrentes sin bloqueo  
3. **Escalabilidad**: Crece linealmente con número de threads
4. **Simplicidad**: Sin complejidad de sincronización manual
