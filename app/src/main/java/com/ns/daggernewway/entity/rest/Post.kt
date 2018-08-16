package com.ns.daggernewway.entity.rest

data class Post(val id: Int,
                val userId: Int,
                val title: String,
                val body: String)