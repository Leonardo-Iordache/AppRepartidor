package com.example.apprepartidor.mainScreen

import androidx.lifecycle.ViewModel
import javax.sql.DataSource

class PackagesListViewModel(val dataSource: DataSource): ViewModel() {
    val packagesLiveData = dataSource.getPackagesList()
}