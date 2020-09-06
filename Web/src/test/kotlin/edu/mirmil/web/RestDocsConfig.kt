package edu.mirmil.web

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor
import org.springframework.restdocs.operation.preprocess.Preprocessors.*

class RestDocsConfig {
    companion object {
        fun getDocsRequest(): OperationRequestPreprocessor {
            return preprocessRequest(
                modifyUris().scheme("http").host("edu.mirmil.net").removePort(),
                prettyPrint()
            )
        }

        fun getDocsResponse(): OperationResponsePreprocessor {
            return preprocessResponse(prettyPrint())
        }
    }
}