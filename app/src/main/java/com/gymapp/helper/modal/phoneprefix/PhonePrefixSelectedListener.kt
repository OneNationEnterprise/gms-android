package com.gymapp.helper.modal.phoneprefix

import com.gymapp.main.data.model.country.Country

interface PhonePrefixSelectedListener {
    fun dialCodeSelected(country: Country)
}