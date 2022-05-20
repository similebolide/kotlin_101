# Kotlin 101

## Syntaxe de base

### Variable

Valeur constante, immutable
```kotlin
val string = "hello"
```
Valeur ré-assignable

```kotlin
var string = "hello"
string = "hello, world"
```


**Note : virer de cette section ?** (elvis + paramètres optionnels)

```val string: String? = "hello"
val string: String? = null

val string = StringGenerator.getStringOrNull() ?: "string generator returned null"
```

### Fonction

#### 2 façons de déclarer

Bloc

````kotlin
fun sum(a: Double, b: Double): Double {
    return a + b
}
````

One-line

````kotlin
fun sum(a: Double, b: Double): Double = a + b
````

#### Argument optionnel

```kotlin
fun printWelcomeMessage(firstName: String?) {
    firstName ?: {
        print("Welcome, $firstname !")
        return
    }
    
    print("Welcome, stranger !")
}
```
#### Argument par défaut

````kotlin
fun printWelcomeMessage(firstName: String = "stranger") {
    print("Welcome, $firstName !")
}
````

## Classes

### Constructors

Membres à assigner à la construction, traditionnellement en Java

```java
class Foo {
    private String bar;
            
    public Foo(String bar) {
        this.bar = bar;
    }
}
```

Équivalent Kotlin

```kotlin
class Foo(val bar: String)
```

### Companion object

#### Variables statiques

Membres de classe (non-liés à une instance). Traditionnellement en Java

```java
class Toto {
    public final static String MY_STATIC_MEMBER = "This is my static member";
}

// ...

    String singletonValue = Toto.MY_STATIC_MEMBER;
// "This is my static member"
```

En kotlin

```kotlin
class Toto {
    companion object {
        val MY_SINGLETON = "This is my static member"
    }
}

val singletonValue = Toto.MY_SINGLETON;
// "This is my static member"
```

Même procédé pour déclarer des méthodes statiques

### Héritage

````kotlin
open class Logger(abstract val logPrefix: String) {
    
    fun log(val message: String) {
        print("$logPrefix: $message")
    }
}

class DebugLogger(): Logger("Debug: ")

val debugLogger = DebugLogger()
debugLogger.log("my first log message")
// "Debug: my first log message"
````

### Operator overloading

`invoke()`: très utile pour les usecase

```kotlin
class RetrieveProducts(val productRepository: ProductRepository) {
    
    override fun invoke(): List<Product> = productsRepository.findAll()
}

// ...

val retrieveProducts = RetrieveProducts(gtsProductRepository)
val products = retrieveProducts() // No need to call retrieveProducts.invoke()!
```
Pour en savoir plus : https://kotlinlang.org/docs/operator-overloading.html

### Method extension

```kotlin
class Product(val id: ProductId, val label: String) {}

// ... elsewhere in the code (can be another file !)

Product.toString() = "Product ${id.value}, with label $label"
```

### Classes avec propriétés spécifiques

Équivalent aux POJO (Plain-Old Java Objects)

```kotlin
data class Payment(
    val transactionId: String,
    val amount: Double,
    val paymentMethod: PaymentMethod
)

// elsewhere in the code
val payment = Payment(
    transactionId = "19537593",
    amount = 1249.99,
    paymentMethod = PaymentMethod.CB
)

// fields access (using destructuring, only available on data classes)
val (transactionId, amount, paymentMethod) = payment
// "19537593", 1249.99, PaymentMethod.CB
```

**TODO**

```kotlin
sealed class SealedClass
```

### Singleton

Accessible partout (variables globales)

```kotlin
object ServerConfiguration(var baseUrl: String) {}

// anywhere else in the code
ServerConfiguration.baseUrl = "https://cda.test.fr/api/v1"

// another place
val gtsProductsResponse = httpClient.call("${ServerConfiguration.baseUrl}/products")
```

### Interfaces (polymorphisme)

Déclaration

```kotlin
interface DateFormatter {
    fun format(date: LocalDate): String
}

class FrontDateFormatter: DateFormatter {
    override fun format(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("EEEEE MMMMM yyyy, à HH:mm:ss")
        return date.format(formatter)
    }
}

class DatadogDateFormatter: DateFormatter {
    override fun format(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSZ")
        return date.format(formatter)
    }
}
```

Injection de dépendance

```kotlin

class RetrieveFormattedProductConsumptionDate(val productRepository: ProductRepository, val dateFormatter: DateFormatter) {
    override fun invoke(val productId: ProductId): String { 
        val product = productRepository.findBy(productId)
        
        return dateFormatter.format(product.consumptionDate)
    }
}
```

Usage en contexte avec polymorphisme

```kotlin
// front-related use case
val frontDateFormatter = FrontDateFormatter()
val retrieveFrontFormattedConsumptionDate = RetrieveFormattedProductConsumptionDate(productsRepository, frontDateFormatter)
val consumptionDateForFront = retrieveFrontFormattedConsumptionDate()
// "
```

```kotlin
// datadog-related use case
val datadogDateFormatter = DatadogDateFormatter()
val retrieveDatadogFormattedConsumptionDate = RetrieveFormattedProductConsumptionDate(productsRepository, datadogDateFormatter)
val consumptionDateForDatadog = retrieveDatadogFormattedConsumptionDate()
// "
```

**ended here**...

## Mots-clés

### Case

```kotlin
when (variable) {
    value1 ->
        // ...
    value2 ->
        // ...
    else ->
        // ...
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

## Transformations

### filter

```kotlin
TODO filter
```

### map / toMap()

Conventionnel (java-ish)

```kotlin
fun getPriceList(productList: List<Product>): List<Double> {
    val priceList = mutableListOf<Product>()

    for (product in productList) {
        priceList.add(product.price)
    }

    return priceList
}

```

À la sauce Kotlin

```kotlin
fun getPriceList(productList: List<Product>): List<Double> =
    productList.map { it.price }
```


### flat map / flatten

```kotlin
TODO flat map
```

### zip

```kotlin
TODO zip
```

### plus / minus

```kotlin
TODO plus minus
```

### groupBy

```kotlin
TODO groupBy
```

### fold / reduce

```
TODO fold / reduce
```

## Scope Functions

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

