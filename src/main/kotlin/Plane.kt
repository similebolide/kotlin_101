class Plane(
    val height: Double,
    speed: Double,
    length: Double,
    modelName: String
) : Vehicle(speed, length, modelName) {
    override fun toString(): String {
        return "Plane {" +
                " $height" +
                " $speed" +
                " $length" +
                " $modelName" +
                " }"
    }
}
