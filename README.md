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
```

...

### Variables statiques

```kotlin
class Toto {
    companion object {
        ...
    }
}
```

### Operator overloading

```kotlin
Toto.toString() = 
```

## Mots-clés

### Case

```kotlin
when(variable) {
    value1 -> ...
    value2 -> ...
    else -> ...
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
