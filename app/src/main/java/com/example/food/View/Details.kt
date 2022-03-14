package com.example.food.View

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.food.R
import com.example.food.Util.Resource
import com.example.food.ViewModel.FoodViewModel
import kotlinx.android.synthetic.main.fragment_details.*


class Details : Fragment(R.layout.fragment_details) {
lateinit var viewModel: FoodViewModel
val args: DetailsArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
        val Food=args.details
        backk.setOnClickListener {
            findNavController().navigate(R.id.action_details_to_home2)

        }

        save_dd.setOnClickListener {
            viewModel.saveFood(Food)
        }


       viewModel.getDetails(Food.idMeal.toString())

        viewModel.Details.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success->{
                    response.data?.let { meal->
                        for (item in meal.meals){
                            Glide.with(this).load(item.strMealThumb).into(imagedet)
                            name_food_D.text=item.strMeal
                            cuntryD.text=item.strArea
                            dec.text=item.strInstructions
                            ingredient.text="""
                                *.${item.strMeasure1+" "+item.strIngredient1}
                                *.${item.strMeasure2 + " " + item.strIngredient2}
                                *.${item.strMeasure3 + " " + item.strIngredient3}
                                *.${item.strMeasure4 + " " + item.strIngredient4}
                                *.${item.strMeasure5 + " " + item.strIngredient5}
                                *.${item.strMeasure6 + " " + item.strIngredient6}
                                *.${item.strMeasure7 + " " + item.strIngredient7}
                                *.${item.strMeasure8 + " " + item.strIngredient8}
                                *.${item.strMeasure9 + " " + item.strIngredient9}
                                *.${item.strMeasure10 + " " + item.strIngredient10}
                                *.${item.strMeasure11 + " " + item.strIngredient11}
                                *.${item.strMeasure12 + " " + item.strIngredient12}
                                *.${item.strMeasure13 + " " + item.strIngredient13}
                                *.${item.strMeasure14 + " " + item.strIngredient14}
                                *.${item.strMeasure15 + " " + item.strIngredient15}
                                *.${item.strMeasure16 + " " + item.strIngredient16}
                                *.${item.strMeasure17 + " " + item.strIngredient17}
                                *.${item.strMeasure18 + " " + item.strIngredient18}
                                *.${item.strMeasure19 + " " + item.strIngredient19}
                                *.${item.strMeasure20 + " " + item.strIngredient20}
                            """.trimIndent()

                            play.setOnClickListener {
                                val i=Intent(Intent.ACTION_VIEW, Uri.parse(item.strYoutube))
                                startActivity(i)
                            }
                        }

                    }

                }
            }
        })
    }

}