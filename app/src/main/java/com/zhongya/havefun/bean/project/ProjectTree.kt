package com.zhongya666.project.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 项目分类
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class ProjectTree(var children: List<String> = listOf(),
                       var courseId: Int = 0,
                       var id: Int = 0,
                       var name: String = "",
                       var order: Int = 0,
                       var parentChapterId: Int = 0,
                       var userControlSetTop: Boolean = false,
                       var visible: Int = 0) : Parcelable
@SuppressLint("ParcelCreator")
@Parcelize
data class ProjectContent(
        val apkLink: String,
        val audit: Int,
        val author: String,
        val canEdit: Boolean,
        val chapterId: Int,
        val chapterName: String,
        var collect: Boolean,
        val courseId: Int,
        val desc: String,
        val descMd: String,
        val envelopePic: String,
        val fresh: Boolean,
        val host: String,
        val id: Int,
        val link: String,
        val niceDate: String,
        val niceShareDate: String,
        val origin: String,
        val prefix: String,
        val projectLink: String,
        val publishTime: Long,
        val realSuperChapterId: Int,
        val selfVisible: Int,
        val shareDate: Long,
        val shareUser: String,
        val superChapterId: Int,
        val superChapterName: String,
        val tags: List<Tag>,
        val title: String,
        val type: Int,
        val userId: Int,
        val visible: Int,
        val zan: Int
): Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Tag(
        val name: String,
        val url: String
): Parcelable