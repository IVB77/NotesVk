data class Notes(
    val title: String,
    var text: String,
    val privacy: Int = 3,
    val commentPrivacy: Int = 3,
    val privacyView: String = "",
    val privacyComment: String = "",
    var isDelete: Boolean = false
)

data class NoteComment(
    val noteId: Int,
    var message: String,
    val ownerId: Int = 10,
    val replyTo: Int = 11,
    val guid: String = "",
    var isDelete: Boolean = false
)

object NotesService {
    private var noteMap: MutableMap<Int, Notes> = mutableMapOf()
    private var commentNote: MutableMap<Int, NoteComment> = mutableMapOf()
    private var idNotes = 0
    private var idComment = 0

    fun <T> add(obj: T): Int? {
        if (obj is Notes) {
            noteMap[++idNotes] = obj
            return idNotes
        } else if (obj is NoteComment) {
            for (id in noteMap.keys) {
                if (id == obj.noteId && !noteMap[id]?.isDelete!!) {
                    commentNote += mapOf(++idComment to obj)
                    return idComment
                }
            }
            println("Данной заметки не существует")
            return null
        }
        return null
    }

    fun delete(obj: String, id: Int): Boolean {
        return when (obj) {
            "Notes" -> {
                noteMap[id]?.isDelete = true
                for (comment in commentNote) {
                    if (comment.value.noteId == id)
                        comment.value.isDelete = true
                }
                noteMap[id] != null
            }
            "Comments" -> {
                commentNote[id]?.isDelete = true
                commentNote[id] != null
            }
            else -> false
        }
    }

    fun <T> edit(id: Int, obj: T): Boolean {
        return if (obj is Notes && noteMap[id] != null && noteMap[id]?.isDelete == false) {
            noteMap[id] = obj
            true
        } else if (obj is NoteComment && commentNote[id] != null && noteMap[obj.noteId] != null && noteMap[obj.noteId]?.isDelete == false) {
            commentNote[id] = obj
            true
        } else false

    }

    fun restore(obj: String, id: Int): Boolean {
        return when (obj) {
            "Notes" -> {
                noteMap[id]?.isDelete = false
                noteMap[id] != null
            }
            "Comments" -> {
                commentNote[id]?.isDelete = false
                commentNote[id] != null
            }
            else -> false
        }
    }

    fun getNote() {
        println("List of Notes")
        for (note in noteMap)
            if (!note.value.isDelete)
                println(note)
        println()
    }

    fun getComments(noteId: Int) {
        println("List of Comments by Notes $noteId")
        for (comment in commentNote)
            if (comment.value.noteId == noteId && !comment.value.isDelete)
                println(comment)
        println()
    }


    fun getById(id: Int): Notes? {
        return noteMap[id]
    }

    fun clearDatabase() {
        commentNote.clear()
        noteMap.clear()
        idNotes = 0
        idComment = 0
    }
}


fun main() {
    println("ADD FUNCTION")
    var note = Notes("1", "23")
    println(NotesService.add(note))
    note = Notes("100", "232")
    println(NotesService.add(note))
    var comment = NoteComment(1, "2")
    println(NotesService.add(comment))
    comment = NoteComment(2, "Hi")
    println(NotesService.add(comment))
    println("DELETE FUNCTION")
    println(NotesService.delete("Notes", 2))
    println(NotesService.delete("Comments", 1))
    NotesService.getNote()
    NotesService.getComments(1)
    println("EDIT FUNCTION")
    println(NotesService.edit(1, Notes("New title", "New text")))
    println(NotesService.edit(1, NoteComment(2, "New message")))
    println(NotesService.edit(3, Notes("New New title", "New New text")))
    println(NotesService.edit(4, NoteComment(2, "New New message")))
    NotesService.getNote()
    NotesService.getComments(2)
    println("RESTORE FUNCTION")
    println(NotesService.restore("Comments", 1))
    NotesService.getComments(1)
    println("GETBYID FUNCTION")
    println(NotesService.getById(1))
    println(NotesService.getById(3))
    println()


}
