package domain.controller
import TestRunner
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test

class WiseSayingControllerTest {

    @Test
    @DisplayName("명언 작성")
    fun t1 () {
        val result = TestRunner.run(
            """
                등록
                나의 죽음을 적들에게 알리지 말라.
                충무공 이순신
            """.trimIndent()
        )

        print("result: $result")

        assertThat(result).contains("명언: ")
        assertThat(result).contains("작가: ")
        assertThat(result).contains("1번 명언이 등록되었습니다.")
    }

    @Test
    @DisplayName("명언 목록")
    fun t2 () {
        val result = TestRunner.run(
            """
                등록
                나의 죽음을 적들에게 알리지 말라.
                충무공 이순신
                등록
                노는게 제일 좋아.
                뽀로로
                목록
            """.trimIndent()
        )

        print("result: $result")

        assertThat(result).contains("1 / 충무공 이순신 / 나의 죽음을 적들에게 알리지 말라.")
        assertThat(result).contains("2 / 뽀로로 / 노는게 제일 좋아.")
    }

    @Test
    @DisplayName("명언 삭제")
    fun t3 () {
        val result = TestRunner.run(
            """
                등록
                나의 죽음을 적들에게 알리지 말라.
                충무공 이순신
                등록
                노는게 제일 좋아.
                뽀로로
                삭제?id=1
                목록
            """.trimIndent()
        )

        print("result: $result")

        assertThat(result).contains("1번 명언이 삭제되었습니다.")
        assertThat(result).doesNotContain("1 / 충무공 이순신 / 나의 죽음을 적들에게 알리지 말라.")
        assertThat(result).contains("2 / 뽀로로 / 노는게 제일 좋아.")
    }

    @Test
    @DisplayName("명언 수정")
    fun t4 () {
        val result = TestRunner.run(
            """
                등록
                나의 죽음을 적들에게 알리지 말라.
                충무공 이순신
                수정?id=1
                노는게 제일 좋아.
                뽀로로
                목록
            """.trimIndent()
        )

        print("result: $result")

        assertThat(result).contains("1번 명언이 수정되었습니다.")
        assertThat(result).doesNotContain("1 / 충무공 이순신 / 나의 죽음을 적들에게 알리지 말라.")
        assertThat(result).contains("1 / 뽀로로 / 노는게 제일 좋아.")
    }

    @Test
    @DisplayName("종료")
    fun t5 () {
        val result = TestRunner.run("")

        print("result: $result")

        assertThat(result).contains("앱을 종료합니다")
    }
}