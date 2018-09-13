package com.ns.daggernewway.di.postcomments

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.ns.daggernewway.di.common.scopes.FragmentScope
import com.ns.daggernewway.entity.ui.FullPost
import com.ns.daggernewway.interactor.getcomments.IGetCommentsInteractor
import com.ns.daggernewway.ui.main.post.CommentsViewModel
import com.ns.daggernewway.ui.main.post.CommentsViewModelFactory
import com.ns.daggernewway.ui.main.post.PostCommentsFragment
import com.ns.daggernewway.ui.main.post.PostCommentsFragment.ArgumentExtractorModule
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module(includes = [ArgumentExtractorModule::class])
class PostCommentsModule {

    @Provides
    @FragmentScope
    fun postDetailsViewModel(fragment: PostCommentsFragment,
                             factory: CommentsViewModelFactory): CommentsViewModel {
        return ViewModelProviders.of(fragment, factory).get(CommentsViewModel::class.java)
    }

    @Provides
    @FragmentScope
    fun providePostCommentsViewModelFactory(interactor: IGetCommentsInteractor,
                                            @Named(ArgumentExtractorModule.QUALIFIER) fullPost: FullPost?,
                                            @Named(ArgumentExtractorModule.QUALIFIER) savedInstanceState: Bundle?): CommentsViewModelFactory {
        return CommentsViewModelFactory(interactor, fullPost, savedInstanceState)
    }

}
