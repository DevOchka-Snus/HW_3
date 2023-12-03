import exceptions.EmptyMapException
import java.util.NoSuchElementException

class AccountRepository {
    private val map = hashMapOf<Int, Account>()

    public fun addAccount(key: Int, account: Account): Account {
        map.put(key, account)
        return account
    }

    public fun getAccount(key:Int): Account? {
        if (!map.containsKey(key)) {
            throw NoSuchElementException(key.toString())
        }

        return map.get(key)
    }

    public fun getAllAccounts(): MutableList<Account> {
        if (map.isEmpty()) {
            throw EmptyMapException()
        }
        val accountList = mutableListOf<Account>();
        for (entry in map.entries) {
            accountList.add(entry.value)
        }
        return accountList
    }
}