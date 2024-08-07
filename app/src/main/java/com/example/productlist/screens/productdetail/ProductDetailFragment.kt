package com.example.productlist.screens.productdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.productlist.databinding.FragmentProductDetailBinding
import com.example.productlist.models.Product
import com.example.productlist.retrofit.ApiClient
import com.example.productlist.retrofit.IProductService

class ProductDetailFragment : Fragment() {

    private  var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private lateinit var productDetailViewModel: ProductDetailViewModel
    private var productId : Int? = null
    private var product : Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            productId = bundle.getInt("productId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        listenEvents()
    }

    private fun init(view : View){
        navController = Navigation.findNavController(view)
        productDetailViewModel = ViewModelProvider(this)[ProductDetailViewModel::class.java]
        productDetailViewModel.productService = ApiClient.getClient().create(IProductService::class.java)
        if(productId != null){
            productDetailViewModel.getProduct(productId!!)
        }
    }

    private fun setProductFields(){
        product?.let { currentProduct->
            var productImages = mutableListOf<SlideModel>()
            for(image in currentProduct.images){
                productImages.add(SlideModel(image, ScaleTypes.CENTER_CROP))
            }
            binding.productImageSlider.setImageList(productImages)
            binding.txtTitle.text = "${currentProduct.brand} ${currentProduct.title}"
            binding.txtDescription.text = currentProduct.description
            binding.txtPrice.text = "$ ${currentProduct.price}"
            binding.txtRate.text = currentProduct.rating.toString()
            binding.txtStock.text = "There are currently ${currentProduct.stock} of this product"
        }
    }


    private fun listenEvents(){
        productDetailViewModel.product.observe(viewLifecycleOwner){result->
            result?.let {currentProduct->
                product = currentProduct
                setProductFields()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}