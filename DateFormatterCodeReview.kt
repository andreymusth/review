class DateFormatter {

    private val cache = HashMap<Long, String>()

    private val months = arrayOf(
        "января",
        "февраля",
        "марта",
        "апрель",
        "мая",
        "июня",
        "июля",
        "августа",
        "сентября",
        "октября",
        "ноября",
        "декабря"
    )

    // принимает long UNIX Time и возвращает месяц + год -> апреля 2024 года.
    // т.е. нам интересно форматирвание в пределах месяца
    // оптимизирована для частых вызовов
    fun formatDate(time: Long): String {
        if (cache.containsKey(time)) {
            return cache[time]!!
        }
        val date = Date(time)
        val formattedDate = getDate(date.year, date.month)
        cache[time] = formattedDate
        return formattedDate
    }

    private fun getDate(year: Int, month: Int): String {
        val monthRu = months[month]
        return monthRu + " " + year + " года"
    }
}
