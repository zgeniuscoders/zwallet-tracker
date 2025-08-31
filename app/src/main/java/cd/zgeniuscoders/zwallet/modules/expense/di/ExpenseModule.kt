package cd.zgeniuscoders.zwallet.modules.expense.di

import cd.zgeniuscoders.zwallet.modules.debts.data.services.DebtServiceImpl
import cd.zgeniuscoders.zwallet.modules.debts.domain.services.DebtService
import cd.zgeniuscoders.zwallet.modules.expense.data.ExpenseServiceImpl
import cd.zgeniuscoders.zwallet.modules.expense.domain.services.ExpenseService
import cd.zgeniuscoders.zwallet.modules.income.data.services.IncomeServiceImpl
import cd.zgeniuscoders.zwallet.modules.income.domain.services.IncomeService
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object expenseModule {

    @Provides
    @Singleton
    fun provideExpenseService(db: FirebaseFirestore): ExpenseService {
        return ExpenseServiceImpl(db)
    }

    @Provides
    @Singleton
    fun provideDebtService(db: FirebaseFirestore): DebtService {
        return DebtServiceImpl(db)
    }


    @Provides
    @Singleton
    fun provideIncomeService(db: FirebaseFirestore): IncomeService {
        return IncomeServiceImpl(db)
    }

}