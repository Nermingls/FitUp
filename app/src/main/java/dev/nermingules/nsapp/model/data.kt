package dev.nermingules.nsapp.model

import com.google.firebase.Timestamp

val sampleDoctorsData = listOf(
    hashMapOf(
        "name" to "Dr. Ahmet Yılmaz",
        "hospital" to "Ankara Şehir Hastanesi",
        "medicalSpecialty" to "Kardiyoloji",
        "comment" to "Hastalarına çok ilgili ve detaylı açıklamalar yapıyor.",
        "downloadUrl" to "https://example.com/dr_ahmet_yilmaz.jpg",
        "uuid" to "123e4567-e89b-12d3-a456-426614174000",
        "date" to Timestamp.now()
    ),
    hashMapOf(
        "name" to "Dr. Elif Kaya",
        "hospital" to "İstanbul Cerrahi Hastanesi",
        "medicalSpecialty" to "Pediatri",
        "comment" to "Çocuklarla çok iyi iletişim kuruyor.",
        "downloadUrl" to "https://example.com/dr_elif_kaya.jpg",
        "uuid" to "223e4567-e89b-12d3-a456-426614174001",
        "date" to Timestamp.now()
    ),
    hashMapOf(
        "name" to "Dr. Mehmet Can",
        "hospital" to "İzmir Devlet Hastanesi",
        "medicalSpecialty" to "Ortopedi",
        "comment" to "Tecrübeli bir doktor, teşhisleri isabetli.",
        "downloadUrl" to "https://example.com/dr_mehmet_can.jpg",
        "uuid" to "323e4567-e89b-12d3-a456-426614174002",
        "date" to Timestamp.now()
    )
)
