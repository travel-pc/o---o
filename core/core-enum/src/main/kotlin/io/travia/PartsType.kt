package io.travia

enum class PartsType (
    val description: String,
    val order: Int){
    CPU("CPU", 1),
    CPU_COOLER("CPU 쿨러", 2),
    MOTHERBOARD("메인보드", 3),
    MEMORY("메모리", 4),
    SSD("SSD",5),
    HDD("HDD",6),
    POWER_SUPPLY("파워 서플라이",8),
    CASE("케이스",9),
    CASE_FAN("케이스 팬", 10),
    GRAPHICS_CARD("그래픽 카드",7),
    ETC("기타", 11),
}