import Point.newPoint2D

class Test_Point extends munit.FunSuite{
  test("Testing 2D point"){
    val p = newPoint2D(1,2)
    assertEquals(p.x,1.toFloat)
    assertEquals(p.y,2.toFloat)
  }
}
