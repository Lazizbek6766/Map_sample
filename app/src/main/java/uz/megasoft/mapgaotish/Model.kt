package uz.megasoft.mapgaotish

data class Model(
    val code: Int,
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
)