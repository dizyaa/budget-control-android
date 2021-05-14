package dizel.budget_control.budget.view.create_category

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import dizel.budget_control.R
import dizel.budget_control.custom.ColorPicker
import dizel.budget_control.databinding.FragmentCreateCategoryBinding

class CreateCategoryFragment : Fragment(R.layout.fragment_create_category) {

    private var _binding: FragmentCreateCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCreateCategoryBinding.bind(view).apply {
            vColorPick.setOnClickListener { openColorPicker() }
        }
        setUpToolbar()
    }

    private fun setUpToolbar() {
        with(binding.vToolBar) {
            (requireActivity() as AppCompatActivity).setSupportActionBar(this)
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { requireActivity().onBackPressed() }
        }
    }

    private fun openColorPicker() {
        ColorPicker(context).apply {
            setOnDismissListener {
                val colorState = ColorStateList.valueOf(color)
                binding.vColorPick.backgroundTintList = colorState
            }
            show()
        }
    }

    companion object {
        const val FRAGMENT_NAME = "CreateCategoryFragment"
    }
}