import org.junit.Test
import ui.cells.CellImpl
import ui.cells.cell
import ui.cells.input
import ui.cells.invoke
import kotlin.test.assertEquals

class CellTest {
    @Test
    fun test1() {
        val text = input("foo")
        val cell = cell {
            text() + "bar"
        }
        assertEquals("foobar", cell())
    }

    @Test
    fun test2() {
        val text = input("foo")
        val c1 = cell {
            text() + "bar"
        }

        val c2 = {
            c1().length.toString()
        }

        val c3 = cell {
            c1() + c2()
        }

        text.value = "b"
        assertEquals("bbar4", c3())
    }

    @Test
    fun testNoRecomputation() {
        val text = input("foo")
        val cell = cell {
            text() + "bar"
        }

        val value1 = cell()
        val countBefore = CellImpl.CALCULATION_COUNT
        val value2 = cell()
        assertEquals(value1, value2)
        assertEquals(countBefore, CellImpl.CALCULATION_COUNT)
    }
}