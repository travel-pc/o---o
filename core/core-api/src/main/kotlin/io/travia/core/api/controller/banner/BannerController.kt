package io.travia.core.api.controller.banner

import io.travia.banner.ReadBannerService
import io.travia.core.api.controller.banner.response.ReadAllBannerContentResponse
import io.travia.core.api.controller.banner.response.ReadAllBannerResponse
import io.travia.core.support.response.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity


@RestController
@RequestMapping("/api/v1/banners")
class BannerController (
    private val readBannerService: ReadBannerService
){

    @GetMapping
    fun readAll(): ResponseEntity<List<ApiResponse<ReadAllBannerResponse>>> {
        val response = readBannerService.readAll().map {
           ReadAllBannerResponse(
                id = it.id,
                description = it.description,
                gradient = it.gradient,
                moveUrl = it.moveUrl,
                notice = it.notice,
                title = it.title,
                contents = it.contents.map {
                    bannerContent -> ReadAllBannerContentResponse(
                        id = bannerContent.id,
                        description = bannerContent.description,
                        imageUrl = bannerContent.imageUrl,
                        title = bannerContent.title
                    )
                }
            )
        }
        return ResponseEntity.ok(response.map { ApiResponse.success(it) })
    }
}