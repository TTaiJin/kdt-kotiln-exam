package domain.repository

import com.ll.domain.entity.WiseSaying
import com.ll.global.bean.SingletonScope
import com.ll.global.config.AppConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test

class WiseSayingFileRepositoryTest {

    private val wiseSayingRepository = SingletonScope.wiseSayingFileRepository

    companion object {
        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            AppConfig.setModeToTest()
        }
    }

    @BeforeEach
    fun setUp() {
        wiseSayingRepository.clear()
    }

    @Test
    @DisplayName("파일 저장 테스트")
    fun t1() {
        val wiseSaying = wiseSayingRepository
            .save(WiseSaying("나의 죽음을 적들에게 알리지 말라.", "충무공 이순신"))

        val filePath = wiseSayingRepository
            .tableDirPath
            .toFile()
            .listFiles()?.find {
                it.name == "${wiseSaying.id}.json"
            }

        assertThat(filePath).isNotNull()
    }

    @Test
    @DisplayName("findById")
    fun t2() {
        val wiseSaying = WiseSaying("나의 죽음을 적들에게 알리지 말라.", "충무공 이순신")
        val savedWiseSaying = wiseSayingRepository.save(wiseSaying)
        val foundWiseSaying = wiseSayingRepository.findById(savedWiseSaying.id)

        assertThat(savedWiseSaying).isEqualTo(foundWiseSaying)
    }

    @Test
    @DisplayName("saveLastId, loadLastId")
    fun t3() {
        wiseSayingRepository.saveLastId(10)
        assertThat(wiseSayingRepository.loadLastId()).isEqualTo(10)
    }

    @Test
    @DisplayName("delete")
    fun t4() {
        val wiseSaying = WiseSaying("나의 죽음을 적들에게 알리지 말라.", "충무공 이순신")
        wiseSayingRepository.save(wiseSaying)

        wiseSayingRepository.delete(wiseSaying.id)
        assertThat(wiseSayingRepository.findById(wiseSaying.id)).isNull()
    }

    @Test
    @DisplayName("findAll")
    fun t5() {
        val wiseSaying1 = WiseSaying("나의 죽음을 적들에게 알리지 말라.", "충무공 이순신")
        val wiseSaying2 = WiseSaying("노는게 제일 좋아", "뽀로로")
        wiseSayingRepository.save(wiseSaying1)
        wiseSayingRepository.save(wiseSaying2)

        val wiseSayings = wiseSayingRepository.findAll()
        assertThat(wiseSayings).containsExactly(wiseSaying1, wiseSaying2)
    }
}
