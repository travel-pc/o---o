package io.travia

import org.junit.jupiter.api.Tag
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor


@Tag("context")
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class ContextTest {
}