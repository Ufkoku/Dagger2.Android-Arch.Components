package com.ns.daggernewway.di.postcomments

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ns.daggernewway.App
import com.ns.daggernewway.di.common.scopes.FragmentScope
import com.ns.daggernewway.entity.ui.FullPost
import com.ns.daggernewway.interactor.getcomments.IGetCommentsInteractor
import com.ns.daggernewway.ui.main.post.CommentsViewModel
import com.ns.daggernewway.ui.main.post.PostCommentsFragment
import com.ns.daggernewway.ui.main.post.PostCommentsFragment.ArgumentExtractorModule
import dagger.Module
import dagger.Provides
import java.lang.IllegalArgumentException
import javax.inject.Named

@Module(includes = [ArgumentExtractorModule::class])
class PostCommentsModule {

    @Provides
    @FragmentScope
    fun postDetailsViewModel(fragment: PostCommentsFragment,
                             factory: PostCommentsViewModelFactory): CommentsViewModel {
        return ViewModelProviders.of(fragment, factory).get(CommentsViewModel::class.java)
    }

    @Provides
    @FragmentScope
    fun providePostCommentsViewModelFactory(app: App,
                                            interactor: IGetCommentsInteractor,
                                            fragment: PostCommentsFragment,
                                            @Named(ArgumentExtractorModule.QUALIFIER) fullPost: FullPost?,
                                            @Named(ArgumentExtractorModule.QUALIFIER) savedInstanceState: Bundle?): PostCommentsViewModelFactory {
        return PostCommentsViewModelFactory(app, interactor, fragment, fullPost, savedInstanceState)
    }

    class PostCommentsViewModelFactory(private val app: App,
                                       private val interactor: IGetCommentsInteractor,
                                       private val postCommentsFragment: PostCommentsFragment,
                                       private val fullPost: FullPost?,
                                       private val savedInstanceState: Bundle?) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return (when {
                savedInstanceState != null -> CommentsViewModel(app, interactor, postCommentsFragment, savedInstanceState)
                fullPost != null -> CommentsViewModel(app, interactor, fullPost)
                else -> throw IllegalArgumentException("Unable to initialize view model")
            }) as T
        }

    }

}
