import Point.newPoint2D

class Test_Point extends munit.FunSuite{
  test("Testing simple 2D point"){
    val p = newPoint2D(1,2)
    assertEquals(p.x,1.toFloat)
    assertEquals(p.y,2.toFloat)
  }

  test("Testing 2D point with x negative") {
    val p = newPoint2D(-1, 2)
    assertEquals(p.x, -1.toFloat)
    assertEquals(p.y, 2.toFloat)
  }

  test("Testing 2D point with y negative") {
    val p = newPoint2D(1, -2)
    assertEquals(p.x, 1.toFloat)
    assertEquals(p.y, -2.toFloat)
  }

  test("Testing 2D point with negative values") {
    val p = newPoint2D(-1, -2)
    assertEquals(p.x, -1.toFloat)
    assertEquals(p.y, -2.toFloat)
  }

  test("Testing 2D point with x decimal") {
    val p = newPoint2D(1.13.toFloat, 2)
    assertEquals(p.x, 1.13.toFloat)
    assertEquals(p.y, 2.toFloat)
  }

  test("Testing 2D point with y decimal") {
    val p = newPoint2D(1, 2.13.toFloat)
    assertEquals(p.x, 1.toFloat)
    assertEquals(p.y, 2.13.toFloat)
  }

  test("Testing 2D point with decimal values") {
    val p = newPoint2D(1.13.toFloat, 2.13.toFloat)
    assertEquals(p.x, 1.13.toFloat)
    assertEquals(p.y, 2.13.toFloat)
  }

  test("Testing 2D point with x negative decimal") {
    val p = newPoint2D(-1.13.toFloat, 2)
    assertEquals(p.x, -1.13.toFloat)
    assertEquals(p.y, 2.toFloat)
  }

  test("Testing 2D point with y negative decimal") {
    val p = newPoint2D(1, -2.13.toFloat)
    assertEquals(p.x, 1.toFloat)
    assertEquals(p.y, -2.13.toFloat)
  }

  test("Testing 2D point with negative decimal values") {
    val p = newPoint2D(-1.13.toFloat, -2.13.toFloat)
    assertEquals(p.x, -1.13.toFloat)
    assertEquals(p.y, -2.13.toFloat)
  }

  test("Testing 2D point with x negative decimal") {
    val p = newPoint2D(+1.13.toFloat, 2)
    assertEquals(p.x, +1.13.toFloat)
    assertEquals(p.y, 2.toFloat)
  }

  test("Testing 2D point with y negative decimal") {
    val p = newPoint2D(1, +2.13.toFloat)
    assertEquals(p.x, 1.toFloat)
    assertEquals(p.y, +2.13.toFloat)
  }

  test("Testing 2D point with negative decimal values") {
    val p = newPoint2D(+1.13.toFloat, +2.13.toFloat)
    assertEquals(p.x, +1.13.toFloat)
    assertEquals(p.y, +2.13.toFloat)
  }
}
