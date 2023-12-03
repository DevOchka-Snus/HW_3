import exceptions.NegativeBalanceException

class Account(_id: Int, _name: String) {
    private var id: Int = _id
    private val name: String = _name
    private var balance: Double = 0.0
        get() = field

    public fun increaseBalance(value: Double) {
        balance += value
    }

    public fun reduceBalance(value: Double) {
        if (balance - value < 0) {
            throw NegativeBalanceException()
        }
        balance -= value;
    }

    override fun toString(): String {
        return "Номер счета = $id, название = '$name', баланс = $balance)"
    }
}