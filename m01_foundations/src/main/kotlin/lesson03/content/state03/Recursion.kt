package lesson03.content.state03

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.fakerConfig
import java.util.*
import kotlin.invoke

private val random = Faker(fakerConfig { random = Random(1L) }).name

fun main() {
    val worker = generateCompany(20_000)
    println(employeeToList(worker))
}

data class Employee(val name: String, val reportsTo: Employee? = null)

fun generateCompany(employeeCount: Int): Employee {
    require(employeeCount >= 2)
    var employee = Employee("Big Boss")
    repeat(employeeCount - 2) {
        employee = Employee(random.firstName(), employee)
    }
    return Employee("Hard Worker", employee)
}

fun Employee.toList(list: MutableList<String> = mutableListOf()): List<String> {
    list.add(name)
    return reportsTo?.toList(list) ?: list
}

val employeeToList = DeepRecursiveFunction<Employee, List<String>> { employee ->
    listOf(employee.name) + if (employee.reportsTo == null) emptyList() else callRecursive(employee.reportsTo)
}
