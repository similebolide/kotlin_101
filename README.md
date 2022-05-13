# Kotlin 101

## Syntaxe de base

### Variable

```kotlin
val string = "hello"

var string = "hello"
string = "hello, world"

val string: String? = "hello"
val string: String? = null

val string = StringGenerator.getStringOrNull() ?: "string generator returned null"
```

### Fonction

#### 2 façons de déclarer

```kotlin
fun getPriceList(productList: List<Product>): List<Double> {
    val priceList = mutableListOf<Product>()

    for (product in productList) {
        priceList.add(product.price)
    }

    return priceList
}

fun getPriceList(productList: List<Product>): List<Double> =
    productList.map { it.price }
```

#### Argument par défaut

```kotlin

```

## Classes

### Types de classe

```kotlin
open class

data class

sealed class

interface class

object class
```

### Constructors

### Companion object

#### Variables statiques

```kotlin
class Toto {
    companion object {
        ...
    }
}
```

### Héritage

...

### Operator overloading

```kotlin
Toto.toString() = 
```

### Method extension

```
TODO Method Extension
```

## Mots-clés

### Case

```kotlin
when (variable) {
    value1 ->
        ...
        value2
    ->
        ...
    else ->
        ...
}
```

### Elvis

```kotlin
?:
```

### String interpolation

```kotlin
val name: String = "Astérix"

val message = "Coucou, $name"
```

### Transformations

#### filter

```kotlin
TODO filter
```

#### map / toMap()

```kotlin
TODO map
```

#### flat map / flatten

```kotlin
TODO flat map
```

#### zip

```kotlin
TODO zip
```

#### plus / minus

```kotlin
TODO plus minus
```

#### groupBy

```kotlin
TODO groupBy
```

#### fold / reduce

```
TODO fold / reduce
```

### Scope Functions

#### let()

```
TODO let()
```

#### run()

```
TODO run()
```

#### with()

```
TODO with()
```

#### apply()

```
TODO apply()
```

#### also()

```
TODO also()
```

