package istu.bacs.submission

enum class Language(val languageId: Int, val languageName: String) {
    C       (0, "C"),
    CPP     (1, "C++"),
    Delphi  (2, "Delphi"),
    FPC     (3, "Free Pascal"),
    Python2 (4, "Python 2"),
    Python3 (5, "Python 3"),
    Mono    (6, "Mono C#");

    companion object {
        fun findById(id: Int) = values().first { it.languageId == id }
    }
}