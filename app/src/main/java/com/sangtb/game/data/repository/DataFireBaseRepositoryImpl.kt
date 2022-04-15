package com.sangtb.game.data.repository

import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.FirebaseFirestore
import com.sangtb.game.data.response.CodeIntroduce
import com.sangtb.game.data.response.Diendanxoc
import com.sangtb.game.data.response.LinkKu
import com.sangtb.game.utils.Const.COLLECTION_CODE_INTRODUCE
import com.sangtb.game.utils.Const.COLLECTION_DIEN_DAN_XOC
import com.sangtb.game.utils.Const.COLLECTION_LINK_KU
import java.lang.Exception
import javax.inject.Inject

class DataFireBaseRepositoryImpl @Inject constructor(
    private val firebaseStore: FirebaseFirestore,
    private val repositoryImpl: IpRepositoryImpl
) :
    DataFireBaseRepository(), OnFailureListener {

    override fun getCodeIntroduce(response: (List<CodeIntroduce>) -> Unit) {
        val listCodeIntroduce = mutableListOf<CodeIntroduce>()

        firebaseStore.collection(COLLECTION_CODE_INTRODUCE).get().addOnSuccessListener {
            repositoryImpl.onShowDialog()
            for (document in it.documents) {
                document.toObject(CodeIntroduce::class.java)?.let { it ->
                    listCodeIntroduce.add(it)
                }
            }
            Log.d("aa", "getCodeIntroduce: $listCodeIntroduce")
            response.invoke(listCodeIntroduce)
        }.addOnFailureListener(this@DataFireBaseRepositoryImpl)
    }

    override fun getLinkDkku(response: (List<LinkKu>) -> Unit) {
        val listLinkKu = mutableListOf<LinkKu>()
        firebaseStore.collection(COLLECTION_LINK_KU).whereEqualTo("dk", "dk").get().addOnSuccessListener {

            for (document in it.documents) {
                document.toObject(LinkKu::class.java)?.let { it ->
                    listLinkKu.add(it)
                }
            }
            Log.d("aa", "getLinkku: $listLinkKu")
            response.invoke(listLinkKu)
        }.addOnFailureListener(this@DataFireBaseRepositoryImpl)
    }

    override fun getLinkDnku(response: (List<LinkKu>) -> Unit) {
        val listLinkKu = mutableListOf<LinkKu>()
        firebaseStore.collection(COLLECTION_LINK_KU).whereEqualTo("dk", "dn").get().addOnSuccessListener {
            for (document in it.documents) {
                document.toObject(LinkKu::class.java)?.let { it ->
                    listLinkKu.add(it)
                }
            }
            Log.d("aa", "getLinkku: $listLinkKu")
            response.invoke(listLinkKu)
        }.addOnFailureListener(this@DataFireBaseRepositoryImpl)
    }

    override fun getLinkDiendanxoc(response: (List<Diendanxoc>) -> Unit) {
        val listDiendanxoc = mutableListOf<Diendanxoc>()
        firebaseStore.collection(COLLECTION_DIEN_DAN_XOC).get()
            .addOnSuccessListener {
                repositoryImpl.onShowDialog()
                for (document in it.documents) {
                    document.toObject(Diendanxoc::class.java)?.let { it ->
                        listDiendanxoc.add(it)
                    }
                }
                Log.d("aa", "getLinkku: $listDiendanxoc")
                response.invoke(listDiendanxoc)
            }.addOnFailureListener(this@DataFireBaseRepositoryImpl)
    }

    override val repository: IpRepository?
        get() = null

    override fun onFailure(p0: Exception) {
        Log.d(TAG, "onFailure: ${p0.message}")
        repositoryImpl.onShowDialog()
        repositoryImpl.onFail(Throwable(p0.message))
    }
}
