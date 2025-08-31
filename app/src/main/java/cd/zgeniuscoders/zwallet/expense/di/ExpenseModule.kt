package cd.zgeniuscoders.zwallet.expense.di

import cd.zgeniuscoders.zwallet.debts.data.services.DebtServiceImpl
import cd.zgeniuscoders.zwallet.expense.data.ExpenseServiceImpl
import cd.zgeniuscoders.zwallet.income.data.services.IncomeServiceImpl
import cd.zgeniuscoders.zwallet.debts.domain.services.DebtService
import cd.zgeniuscoders.zwallet.expense.domain.services.ExpenseService
import cd.zgeniuscoders.zwallet.income.domain.services.IncomeService
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