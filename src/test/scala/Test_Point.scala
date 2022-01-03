import Point.{newPoint2D, newPoint3D}

class Test_Point extends munit.FunSuite{
  // Testing 2D points
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

  //#######################################################################################
  // Testing 3D points
  test("Testing simple 3D point"){
    val p = newPoint3D(1,2,3)
    assertEquals(p.x,1.toFloat)
    assertEquals(p.y,2.toFloat)
    assertEquals(p.z,3.toFloat)
  }

  test("Testing 3D point : negative x"){
    val p = newPoint3D(-1,2,3)
    assertEquals(p.x,-1.toFloat)
    assertEquals(p.y,2.toFloat)
    assertEquals(p.z,3.toFloat)
  }
  test("Testing 3D point : negative y"){
    val p = newPoint3D(1,-2,3)
    assertEquals(p.x,1.toFloat)
    assertEquals(p.y,-2.toFloat)
    assertEquals(p.z,3.toFloat)
  }
  test("Testing 3D point : negative z"){
    val p = newPoint3D(1,2,-3)
    assertEquals(p.x,1.toFloat)
    assertEquals(p.y,2.toFloat)
    assertEquals(p.z,-3.toFloat)
  }
  test("Testing 3D point : all negative"){
    val p = newPoint3D(-1,-2,-3)
    assertEquals(p.x,-1.toFloat)
    assertEquals(p.y,-2.toFloat)
    assertEquals(p.z,-3.toFloat)
  }

  test("Testing 3D point : float x"){
    val p = newPoint3D(1.4.toFloat,2,3)
    assertEquals(p.x,1.4.toFloat)
    assertEquals(p.y,2.toFloat)
    assertEquals(p.z,3.toFloat)
  }
  test("Testing 3D point : float y"){
    val p = newPoint3D(1,2.5.toFloat,3)
    assertEquals(p.x,1.toFloat)
    assertEquals(p.y,2.5.toFloat)
    assertEquals(p.z,3.toFloat)
  }
  test("Testing 3D point : float z"){
    val p = newPoint3D(1,2,3.6.toFloat)
    assertEquals(p.x,1.toFloat)
    assertEquals(p.y,2.toFloat)
    assertEquals(p.z,3.6.toFloat)
  }
  test("Testing 3D point : all float"){
    val p = newPoint3D(1.4.toFloat,2.5.toFloat,3.6.toFloat)
    assertEquals(p.x,1.4.toFloat)
    assertEquals(p.y,2.5.toFloat)
    assertEquals(p.z,3.6.toFloat)
  }

  test("Testing 3D point : explicitly positive x"){
    val p = newPoint3D(+1,2,3)
    assertEquals(p.x,+1.toFloat)
    assertEquals(p.y,2.toFloat)
    assertEquals(p.z,3.toFloat)
  }
  test("Testing 3D point : explicitly positive y"){
    val p = newPoint3D(1,+2,3)
    assertEquals(p.x,1.toFloat)
    assertEquals(p.y,+2.toFloat)
    assertEquals(p.z,3.toFloat)
  }
  test("Testing 3D point : explicitly positive z"){
    val p = newPoint3D(1,2,+3)
    assertEquals(p.x,1.toFloat)
    assertEquals(p.y,2.toFloat)
    assertEquals(p.z,+3.toFloat)
  }
  test("Testing 3D point : all explicitly positive"){
    val p = newPoint3D(+1,+2,+3)
    assertEquals(p.x,+1.toFloat)
    assertEquals(p.y,+2.toFloat)
    assertEquals(p.z,+3.toFloat)
  }

  test("Testing 3D point : negative and float x"){
    val p = newPoint3D(+1.4.toFloat,2,3)
    assertEquals(p.x,+1.4.toFloat)
    assertEquals(p.y,2.toFloat)
    assertEquals(p.z,3.toFloat)
  }
  test("Testing 3D point : explicitly positive and float x"){
    val p = newPoint3D(+1.4.toFloat,2,3)
    assertEquals(p.x,+1.4.toFloat)
    assertEquals(p.y,2.toFloat)
    assertEquals(p.z,3.toFloat)
  }
  test("Testing 3D point : negative and float y"){
    val p = newPoint3D(1,-2.5.toFloat,3)
    assertEquals(p.x,1.toFloat)
    assertEquals(p.y,-2.5.toFloat)
    assertEquals(p.z,3.toFloat)
  }
  test("Testing 3D point : explicitly positive  and float y"){
    val p = newPoint3D(1,+2.5.toFloat,3)
    assertEquals(p.x,1.toFloat)
    assertEquals(p.y,+2.5.toFloat)
    assertEquals(p.z,3.toFloat)
  }
  test("Testing 3D point : negative and float z"){
    val p = newPoint3D(1,2,-3.6.toFloat)
    assertEquals(p.x,1.toFloat)
    assertEquals(p.y,2.toFloat)
    assertEquals(p.z,-3.6.toFloat)
  }
  test("Testing 3D point : explicitly positive and float z"){
    val p = newPoint3D(1,2,+3.6.toFloat)
    assertEquals(p.x,1.toFloat)
    assertEquals(p.y,2.toFloat)
    assertEquals(p.z,+3.6.toFloat)
  }
  test("Testing 3D point : negative and float x"){
    val p = newPoint3D(-1.4.toFloat,-2.5.toFloat,-3.6.toFloat)
    assertEquals(p.x,-1.4.toFloat)
    assertEquals(p.y,-2.5.toFloat)
    assertEquals(p.z,-3.6.toFloat)
  }
  test("Testing 3D point : explicitly positive and float x"){
    val p = newPoint3D(+1.4.toFloat,+2.5.toFloat,+3.6.toFloat)
    assertEquals(p.x,+1.4.toFloat)
    assertEquals(p.y,+2.5.toFloat)
    assertEquals(p.z,+3.6.toFloat)
  }
}
