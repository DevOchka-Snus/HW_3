import exceptions.EmptyMapException
import exceptions.NegativeBalanceException

class AccountService {
    private val accountRepository = AccountRepository()
    private var idCounter = 1

    public fun getAllAccounts(): String {
        try {
            val accountList = accountRepository.getAllAccounts()
            val stringBuilder = StringBuilder()

            for (account in accountList) {
                stringBuilder.append(account.toString())
                stringBuilder.append("\n")
            }

            return stringBuilder.toString()
        } catch (e: EmptyMapException) {
            throw e
        }
    }

    public fun createAccount(name: String): Account {
        val account = Account(idCounter, name)
        val savedAccount = accountRepository.addAccount(idCounter, account)
        ++idCounter
        return savedAccount
    }

    public fun refillAccount(id: Int, value: Double) {
        try {
            val account = accountRepository.getAccount(id)
            account?.increaseBalance(value)
        } catch (e: NoSuchElementException) {
            throw e
        }
    }

    public fun transfer(supplierId: Int, consumerId: Int, value: Double) {
        try {
            val supplier = accountRepository.getAccount(supplierId)
            val consumer = accountRepository.getAccount(consumerId)
            supplier?.reduceBalance(value)
            consumer?.increaseBalance(value)
        } catch (e: NoSuchElementException) {
            throw e
        } catch (e: NegativeBalanceException) {
            throw e
        }
    }
}