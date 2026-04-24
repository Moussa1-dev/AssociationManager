package com.association.manager.data.repository

import androidx.lifecycle.LiveData
import com.association.manager.data.dao.MemberDao
import com.association.manager.data.model.Member
import com.association.manager.data.model.MemberRole
import com.association.manager.data.model.MemberStatus

class MemberRepository(private val memberDao: MemberDao) {

    val allMembers: LiveData<List<Member>> = memberDao.getAllMembers()
    val memberCount: LiveData<Int> = memberDao.getMemberCount()

    suspend fun insert(member: Member): Long = memberDao.insert(member)

    suspend fun update(member: Member) = memberDao.update(member)

    suspend fun delete(member: Member) = memberDao.delete(member)

    fun getMemberById(id: Long): LiveData<Member?> = memberDao.getMemberById(id)

    suspend fun getMemberByIdSync(id: Long): Member? = memberDao.getMemberByIdSync(id)

    suspend fun getMemberByEmail(email: String): Member? = memberDao.getMemberByEmail(email)

    suspend fun login(email: String, password: String): Member? = memberDao.login(email, password)

    fun getMembersByStatus(status: MemberStatus): LiveData<List<Member>> =
        memberDao.getMembersByStatus(status)

    fun getMembersByRole(role: MemberRole): LiveData<List<Member>> =
        memberDao.getMembersByRole(role)

    fun getActiveMemberCount(): LiveData<Int> =
        memberDao.getMemberCountByStatus(MemberStatus.ACTIVE)

    fun searchMembers(query: String): LiveData<List<Member>> =
        memberDao.searchMembers(query)
}
