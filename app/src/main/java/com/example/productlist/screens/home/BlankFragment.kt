package com.example.productlist.screens.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.productlist.R
import com.example.productlist.databinding.FragmentBlankBinding


class BlankFragment : Fragment() {

    private  var _binding: FragmentBlankBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBlankBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        binding.btProducts.setOnClickListener {
            navController.navigate(R.id.action_blankFragment_to_homeFragment)
        }
    }

    private fun init(view : View){
        navController = Navigation.findNavController(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}