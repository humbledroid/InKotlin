package learning.coroutines.standard

import io.github.serpro69.kfaker.faker

data class Employee(val name: String, val reportsTo: Employee? = null)

fun generateCompany(employeeCount: Int): Employee {
    require(employeeCount >= 2) { "employee count at least be 2" }

    var employee = Employee("Big boss")
    repeat(employeeCount - 2) {
        employee = Employee(faker{}.name.firstName(), employee)
    }

    return employee
}

fun main() {
    val worker = generateCompany(20_000)
    //println(worker)

    println(employeeToListDR(worker))
}

fun employeeToList(employees: Employee): List<String> {
    return listOf(employees.name) + if(employees.reportsTo == null) emptyList() else employeeToList(employees.reportsTo)
}

val employeeToListDR = DeepRecursiveFunction { employee: Employee ->
    listOf(employee.name) + if(employee.reportsTo == null) emptyList() else callRecursive(employee.reportsTo)
}