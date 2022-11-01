import org.junit.Test

import org.junit.Assert.*

class NotesServiceTest {

    @Test
    fun addNotes() {
        val note = Notes("1", "23")
        val result = NotesService.add(note)
        assert(result != null)
    }

    @Test
    fun addComment() {
        val comment = NoteComment(1, "2")
        val result = NotesService.add(comment)
        assert(result != null)
    }

    @Test
    fun addNull() {
        val result = NotesService.add(2)
        assert(result == null)
    }

    @Test
    fun addCommentFalseNotes() {
        val comment = NoteComment(2, "2")
        val result = NotesService.add(comment)
        assert(result == null)
    }

    @Test
    fun addCommentFalseIsDelete() {
        NotesService.clearDatabase()
        NotesService.add(Notes("1", "2", isDelete = true))
        val comment = NoteComment(1, "2")
        val result = NotesService.add(comment)
        assert(result == null)
    }

    @Test
    fun deleteNote() {
        NotesService.clearDatabase()
        NotesService.add(Notes("1", "2"))
        assertTrue(NotesService.delete("Notes", 1))
    }

    @Test
    fun deleteComment() {
        NotesService.clearDatabase()
        NotesService.add(Notes("1", "2"))
        NotesService.add(NoteComment(1, "2"))
        assertTrue(NotesService.delete("Comments", 1))
    }

    @Test
    fun deleteCommentFalse() {
        NotesService.clearDatabase()
        NotesService.add(Notes("1", "2"))
        NotesService.add(NoteComment(1, "2"))
        assertFalse(NotesService.delete("Comments", 2))
    }

    @Test
    fun deleteNoteFalse() {
        NotesService.clearDatabase()
        NotesService.add(Notes("1", "2"))
        assertFalse(NotesService.delete("Notes", 2))
    }

    @Test
    fun deleteFalse() {
        assertFalse(NotesService.delete("Coments", 1))
    }

    @Test
    fun editNote() {
        NotesService.clearDatabase()
        NotesService.add(Notes("1", "2"))
        assertTrue(NotesService.edit(1, Notes("New title", "New text")))
    }

    @Test
    fun editComment() {
        NotesService.clearDatabase()
        NotesService.add(Notes("1", "2"))
        NotesService.add(NoteComment(1, "2"))
        assertTrue(NotesService.edit(1, NoteComment(1, "New message")))
    }

    @Test
    fun editFalseObj() {
        NotesService.clearDatabase()
        NotesService.add(Notes("1", "2"))
        assertFalse(NotesService.edit(1, 2))
    }

    @Test
    fun restoreNotes() {
        NotesService.clearDatabase()
        NotesService.add(Notes("1", "2"))
        NotesService.add(NoteComment(1, "2"))
        assertTrue(NotesService.restore("Notes", 1))
    }

    @Test
    fun restoreComment() {
        NotesService.clearDatabase()
        NotesService.add(Notes("1", "2"))
        NotesService.add(NoteComment(1, "2", isDelete = true))
        assertTrue(NotesService.restore("Comments", 1))
    }

    @Test
    fun restoreFalseObj() {
        NotesService.clearDatabase()
        NotesService.add(Notes("1", "2"))
        NotesService.add(NoteComment(1, "2", isDelete = true))
        assertFalse(NotesService.restore("Coments", 1))
    }

    @Test
    fun getNote() {
        NotesService.getNote()
    }

    @Test
    fun getComments() {
        NotesService.getComments(1)
    }

    @Test
    fun getById() {
        NotesService.getById(1)
    }
}