fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments at Run/Debug configuration
    println("Program arguments: ${args.joinToString()}")
}

data class Product(
    val id: Int,
    val price: Price,
)

data class Price(
    val value: Int,
    val currency: String = "Euro",
)

fun oldSchoolGetPriceList(productList: List<Product>): List<Price> {
    val priceList = mutableListOf<Price>()
    for (product in productList) {
        priceList.add(product.price)
    }
    return priceList
}

fun getPriceList(productList: List<Product>): List<Price> =
    productList.map { it.price }

