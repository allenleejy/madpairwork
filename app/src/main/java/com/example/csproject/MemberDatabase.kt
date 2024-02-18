package com.example.csproject

class MemberDatabase {
    public var memberList = ArrayList<Member>()

    fun addMember(m: Member) {
        memberList.add(m)
    }

    fun checkIfMember(m:Member): String {
        for (mem in memberList){
            if (mem.username == m.username && mem.password == m.password){
                return "successful"
            }
            else if (mem.username == m.username){
                return "wrong passwd"
            }
        }
        return "no such person"
    }
}