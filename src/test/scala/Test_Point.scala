import Point.newPoint2D

class Test_Point extends munit.FunSuite{
  test("Testing 2D point"){
    val p = newPoint2D(1,2)
    assertEquals(p.getX,1)
    assertEquals(p.getY,2)
  }
}
