package uz.megasoft.mapgaotish.model_map

import com.google.gson.annotations.SerializedName

data class Bounds(@SerializedName("southwest")
                  val southwest: Southwest,
                  @SerializedName("northeast")
                  val northeast: Northeast
)