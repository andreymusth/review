interface CardInfoApi {

    fun getCardInfoById(id: String): Observable<CardInfo>
}
