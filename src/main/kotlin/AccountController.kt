import exceptions.EmptyMapException
import exceptions.NegativeBalanceException

class AccountController {
    private val accountService = AccountService()
    private var isExit = false

    public fun run() {
        while (!isExit) {
            printCommand()

            val command = readln()

            when {
                command.equals("1") -> watchAccountList()
                command.equals("2") -> createNewAccount()
                command.equals("3") -> {
                    var isDone = refillAccount()
                    while (!isDone) {
                        isDone = refillAccount()
                    }
                }
                command.equals("4") -> {
                    var isDone = transfer()
                    while (!isDone) {
                        isDone = transfer()
                    }
                }
                command.equals("5") -> isExit = true
                else -> println("Не существует такой команды!!!\n")
            }
            Thread.sleep(1000)
        }
    }

    private fun transfer(): Boolean {
        try {
            var supplierId:Int?
            while (true) {
                println("Введите номер счета, с которого хотите перевести деньги:")
                supplierId = readln().toIntOrNull()
                if (supplierId == null || supplierId <= 0) {
                    println("Введите корректное значение!!!\n")
                    continue
                }
                break
            }

            var consumerId:Int?
            while (true) {
                println("Введите номер счета, на который хотите перевести деньги:")
                consumerId = readln().toIntOrNull()
                if (consumerId == null || consumerId <= 0) {
                    println("Введите корректное значение!!!\n")
                    continue
                } else if (consumerId == supplierId) {
                    println("Номера счетов совпадают!\n")
                    continue
                }
                break
            }

            var value:Double?
            while (true) {
                println("Введите сумму перевода:")
                value = readln().toDoubleOrNull()
                if (value == null || value < 0) {
                    println("Введите корректное число!!!\n")
                    continue
                }
                break
            }

            accountService.transfer(supplierId!!, consumerId!!, value!!)
            println("Перевод успешно выполнен!\n")
            return true
        } catch (e: NoSuchElementException) {
            println("Не существует счета с номером ${e.message}!!!\n")
            return false
        } catch (e: NegativeBalanceException) {
            println("На счете, с которого вы хотите списать, нет такой суммы!!!\n")
            return false
        }
    }

    private fun refillAccount(): Boolean {
        try {
            var key:Int?
            while (true) {
                println("Введите номер счета:")
                key = readln().toIntOrNull()
                if (key == null || key <= 0) {
                    println("Введите целое число!!!\n")
                    continue
                }
                break
            }

            var value:Double?
            while (true) {
                println("Введите сумму, на которую хотите пополнить счет:")
                value = readln().toDoubleOrNull()
                if (value == null || value < 0) {
                    println("Введите корректное число!!!\n")
                    continue
                }
                break
            }

            accountService.refillAccount(key!!, value!!)
            println("Счет успешно пополнен!\n")
            return true
        } catch (e:NoSuchElementException) {
            println("Не существует счета ${e.message}!!!\n")
            return false
        }
    }

    private fun createNewAccount() {
        println("Введите название нового счета:")
        val name = readln()
        val savedAccount = accountService.createAccount(name)
        println("Создан новый счет:")
        println(savedAccount.toString())
        println()
    }

    private fun watchAccountList() {
        try {
            val accounts = accountService.getAllAccounts()
            println("Список счетов:")
            print(accounts)
            println()
        } catch (e: EmptyMapException) {
            println("У вас нет открытых счетов!!!\n")
        }
    }

    private fun printCommand() {
        println("Список команд:")
        println("1 - Просмотр списка счетов")
        println("2 - Открытие счета")
        println("3 - Пополнение счета")
        println("4 - Перевод денег межу счетами")
        println("5 - Выход")
    }
}