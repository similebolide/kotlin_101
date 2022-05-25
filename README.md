# Kotlin 101

## Syntaxe de base

### Variable

Valeur constante, immutable
```kotlin
val message = "hello"
// message = ... would result in compilation error
```
Valeur ré-assignable

```kotlin
var message = "hello"
// string: hello
string = "hello, world"
// string: hello, world
```
### Fonction

#### Déclaration

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

#### Variables statiques (membres de classe non-liés à une instance)

Traditionnellement en Java

```java
class Toto {
    public final static String MY_STATIC_MEMBER = "This is my static member";
}

// elsewhere in the code

String singletonValue = Toto.MY_STATIC_MEMBER;
// singletonValue: "This is my static member"
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

Pour pouvoir hériter d'une classe : mot-clé `open`

Un membre non défini dans la classe mère (cf. `logPrefix` ci-dessous) doit être déclaré `abstract`.

````kotlin
open class Logger(val logPrefix: String) {
    
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

Les opérateurs du langage (`+`, `+=`, `()`...) peuvent être surchargés pour les objets d'une classe donnée.

ex. `invoke()`: très utile pour les usecase

```kotlin
class RetrieveProducts(val productRepository: ProductRepository) {
    
    override fun invoke(): List<Product> = productsRepository.findAll()
}

// ...

val retrieveProducts = RetrieveProducts(gtsProductRepository)
val products = retrieveProducts() // No need to call retrieveProducts.invoke() :-)
```
Pour en savoir plus : https://kotlinlang.org/docs/operator-overloading.html

### Method extension

Définition d'une méthode de classe n'importe où en-dehors du corps de cette classe (`{}`)

```kotlin
class Product(val id: ProductId, val label: String) {}

// ... elsewhere in the code (can be another file !)

Product.toString() = "Product ${id.value}, with label $label"
```

Note: peut aussi s'appliquer aux collections (`List`...)

```kotlin
fun List<SimplePrice>.sumOfAmounts(currency: CurrencyCode) = SimplePrice(
    currency = currency,
    amount = sumOf { it.amount }
)
```

### Classes avec propriétés spécifiques

#### `data class` : équivalent aux POJO (Plain-Old Java Objects)

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

#### Classes "sealed"

Permet de définir un arbre d'héritage qui ne pourra pas être étendu ailleurs que dans le package concerné. Rajouter `sealed` rajoute la contrainte que toutes les méthodes étendant de la classe en question sont connues à la compilation et présentes dans le package.

> Sealed classes are designed to be used when there are a very specific set of possible options for a value, and where each of these options is functionally different – just Algebraic Data Types.

Pour plus d'infos : https://www.baeldung.com/kotlin/sealed-classes

```kotlin
sealed class Order {
    abstract val id: OrderId
    abstract val site: Site
    abstract val totalPrice: CompositePrice
    // ...
    abstract fun format(formatProvider: FormatProvider): FormattedOrderForConfirmationEmailTemplate

    data class OrderWithTickets(
        override val id: OrderId,
        override val site: Site,
        // ...
        val articles: List<OrderArticle>,
        val couponCode: CouponCode? = null,
        val participantsDetails: List<OrderPassParticipant> = emptyList()
    ) : Order() {
        override val totalPrice
            get() = CompositePrice.ofCompositePrices(site.currency, priceAfterCoupon)

        val priceBeforeDiscount
            get() = CompositePrice.ofCompositePrices(site.currency, priceBeforeCoupon)
        
        // ...

        override fun format(formatProvider: FormatProvider) = FormattedOrderWithTicketsForConfirmationEmailTemplate(
            id = id.value,
            site = site.name,
            // ...
        )
    }

    data class OrderWithStay(
        override val id: OrderId,
        override val site: Site,
        // ...
        val stay: OrderStay,
        val hotel: Hotel
    ) : Order() {

        override val totalPrice
            get() = CompositePrice(site.currency, listOf(stay.totalPriceIncludingOptionsAndInsurances))

        override fun format(formatProvider: FormatProvider) = FormattedOrderWithStayForConfirmationEmailTemplate(
            id = id.value,
            site = site.name,
            // ...
        )
    }
}
```
#### Classes "enum"

Classe décrivant un ensemble fini de valeurs possibles assignables à un objet. `enum class` permet aussi de définir des définitions de méthodes spécifiques à chaque valeur de l'`enum`.

```kotlin
enum class Site(
    val timeZone: ZoneId,
    val currency: CurrencyCode,
    val hasB2CSales: Boolean,
    val hasStays: Boolean,
    val needsEmailSending: Boolean,
    val country: Country
) {
    PARC_ASTERIX(
        timeZone = ZoneId.of("Europe/Paris"),
        currency = EUR,
        hasB2CSales = true,
        hasStays = true,
        needsEmailSending = true,
        country = FRANCE
    ),
    FRANCE_MINIATURE(
        timeZone = ZoneId.of("Europe/Paris"),
        currency = EUR,
        hasB2CSales = true,
        hasStays = false,
        needsEmailSending = true,
        country = FRANCE
    ),
    // ...
}
```
**ended here, to be completed**

#### Classes "inline"

Utile pour wrapper un type primitif dans une class qui reflète la logique métier

```kotlin
value class TicketId(val value: String)
```

Avantage : beaucoup plus performant que l'usage équivalent sans le mot-clé `value`

Pour aller plus loin : https://kotlinlang.org/docs/inline-classes.html

## Singleton

Accessible partout (variables globales)

```kotlin
object ServerConfiguration(var baseUrl: String) {}

// anywhere else in the code
ServerConfiguration.baseUrl = "https://cda.test.fr/api/v1"

// another place
val gtsProductsResponse = httpClient.call("${ServerConfiguration.baseUrl}/products")
```

## Interfaces (polymorphisme)

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
