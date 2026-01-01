package lesson25.content.state05

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

class ReportViewModel(private val appScope: CoroutineScope) : ViewModel() {
    fun loadExistingReports() = viewModelScope.launch { /* ... */ }

    fun processWorkLogReport(report: WorkLogReport) = appScope.launch { /* ... */ }
}

open class ViewModel {
    val viewModelScope: CoroutineScope = CoroutineScope(EmptyCoroutineContext)
}

class WorkLogReport
