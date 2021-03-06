package dizel.budget_control.budget.view.create_budget

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dizel.budget_control.R
import dizel.budget_control.budget.domain.entity.Currency
import dizel.budget_control.budget.view.budget_details.BudgetDetailsFragment
import dizel.budget_control.budget.view.budget_list.BudgetListFragment
import dizel.budget_control.databinding.FragmentCreateBudgetBinding
import dizel.budget_control.core.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateBudgetFragment: Fragment(R.layout.fragment_create_budget) {
    private var _binding: FragmentCreateBudgetBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<CreateBudgetViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCreateBudgetBinding.bind(view).apply {
            vSubmitButton.setOnClickListener { createBudget() }

            val adapter = ArrayAdapter(
                view.context,
                android.R.layout.simple_spinner_dropdown_item,
                Currency.values().map { "${it.name} - ${it.symbol}" }
            )
            vSpinnerCurrency.adapter = adapter
        }

        setUpToolbar()
    }

    private fun navigateToBudgetDetails(id: String) {
        val fragment = BudgetDetailsFragment.newInstance(id)
        parentFragmentManager.popBackStack()
        startFragment(fragment, BudgetListFragment.FRAGMENT_NAME)
    }

    private fun createBudget() {
        val title = binding.vNameBudget.text.toString().ifEmpty { null }
        val money = binding.vMoneyBudget.text.toString().toDoubleOrNull()

        if (title == null || money == null) {
            showError(getString(R.string.invalidate_fields))
            return
        }

        val currency = Currency.values()[binding.vSpinnerCurrency.selectedItemPosition]
        val params = CreateBudgetViewModel.NewBudgetParams(title, money, currency)

        viewModel.createBudget(params).observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultRequest.Success -> {
                    val id = result.data
                    navigateToBudgetDetails(id)
                }
                is ResultRequest.Error -> {
                    showError(result.exception.message ?: getString(R.string.unknown_error))
                }
                is ResultRequest.Loading -> { }
            }
        }
    }

    private fun setUpToolbar() {
        with(binding.vToolBar) {
            (requireActivity() as AppCompatActivity).setSupportActionBar(this)
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { requireActivity().onBackPressed() }
        }
    }

    private fun showError(mes: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.error_stub_title)
            .setMessage(mes)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}