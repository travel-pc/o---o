package io.travia.core.api.controller.banner

import io.travia.banner.BannerService
import io.travia.core.api.controller.banner.response.ReadAllBannerContentResponse
import io.travia.core.api.controller.banner.response.ReadAllBannerResponse
import io.travia.core.support.response.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity


@RestController
@RequestMapping("/api/v1")
class BannerController (
    private val bannerService: BannerService
){

    @GetMapping("/banners/with-contents")
    fun readAll(): ResponseEntity<ApiResponse<List<ReadAllBannerResponse>>> {
        val response = bannerService.readAll().map {
           ReadAllBannerResponse(
                id = it.bannerId,
                description = it.description,
                gradient = it.gradient,
                moveUrl = it.moveUrl,
                notice = it.notice,
                title = it.title,
                contents = it.contents.map {
                    bannerContent -> ReadAllBannerContentResponse(
                        id = bannerContent.bannerContentId,
                        description = bannerContent.description,
                        imageUrl = bannerContent.imageUrl,
                        title = bannerContent.title
                    )
                }
            )
        }

        return ResponseEntity.ok(ApiResponse.success(response))
    }
}