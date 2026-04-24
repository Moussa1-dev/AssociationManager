package com.association.manager.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.association.manager.data.model.Member
import com.association.manager.data.model.MemberRole
import com.association.manager.data.model.MemberStatus

@Dao
interface MemberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(member: Member): Long

    @Update
    suspend fun update(member: Member)

    @Delete
    suspend fun delete(member: Member)

    @Query("SELECT * FROM members ORDER BY lastName, firstName")
    fun getAllMembers(): LiveData<List<Member>>

    @Query("SELECT * FROM members WHERE id = :id")
    fun getMemberById(id: Long): LiveData<Member?>

    @Query("SELECT * FROM members WHERE id = :id")
    suspend fun getMemberByIdSync(id: Long): Member?

    @Query("SELECT * FROM members WHERE email = :email LIMIT 1")
    suspend fun getMemberByEmail(email: String): Member?

    @Query("SELECT * FROM members WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): Member?

    @Query("SELECT * FROM members WHERE status = :status ORDER BY lastName, firstName")
    fun getMembersByStatus(status: MemberStatus): LiveData<List<Member>>

    @Query("SELECT * FROM members WHERE role = :role ORDER BY lastName, firstName")
    fun getMembersByRole(role: MemberRole): LiveData<List<Member>>

    @Query("SELECT COUNT(*) FROM members")
    fun getMemberCount(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM members WHERE status = :status")
    fun getMemberCountByStatus(status: MemberStatus): LiveData<Int>

    @Query("SELECT * FROM members WHERE firstName LIKE '%' || :query || '%' OR lastName LIKE '%' || :query || '%' OR email LIKE '%' || :query || '%'")
    fun searchMembers(query: String): LiveData<List<Member>>
}
