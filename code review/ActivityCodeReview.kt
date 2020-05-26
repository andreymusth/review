class UserProfileActivity : AppCompatActivity(), View.OnClickListener, UserProfileView {

    private val presenter: UserProfilePresenter = UserProfilePresenterImpl(this)

    @Inject
    lateinit var api: CardInfoApi

    override fun onCreate(savedInstanceState: Bundle?) {
        App.component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameTextView = findViewById<TextView>(R.id.name_text)
        val user = presenter.getUser()
        nameTextView.text = user.name

        findViewById<Button>(R.id.log_out_button).setOnClickListener(this)

        val requestCardInformationButton = findViewById<Button>(R.id.card_information_button)
        requestCardInformationButton.setOnClickListener {

            api.getCardInfoById(user.id)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        showInfoDialog(it.info)
                    },
                    {}
                )
        }

    }

    fun showInfoDialog(info: String) {
        // some irrelevant code
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.log_out_button) {
            presenter.onLogoutClicked()
        }
    }

    override fun showConfirmLogoutDialog() {
        showLogoutDialog(confirmAction = {
            presenter.onLogoutConfirmed()
            finish()
        })
    }

    private fun showLogoutDialog(confirmAction: (() -> Unit)? = null) {
        // some irrelevant code
    }
}

interface UserProfileView {

    fun showConfirmLogoutDialog()
}

interface UserProfilePresenter {

    fun onLogoutClicked()

    fun onLogoutConfirmed()

    fun getUser(): User
}

interface CardInfoApi {

    fun getCardInfoById(id: String): Observable<CardInfo>
}

class User(
    val id: String,
    val name: String
)

data class CardInfo(
    val info: String
)
