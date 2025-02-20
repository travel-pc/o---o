package io.travia.test.api

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor
import org.springframework.restdocs.operation.preprocess.Preprocessors

object RestDocsUtils {
    fun requestPreprocessor(): OperationRequestPreprocessor {
        return Preprocessors.preprocessRequest(
            Preprocessors.modifyUris().scheme("http").host("io.travel.pc").removePort(),
            Preprocessors.prettyPrint(),
        )
    }

    fun responsePreprocessor(): OperationResponsePreprocessor {
        return Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
    }
}