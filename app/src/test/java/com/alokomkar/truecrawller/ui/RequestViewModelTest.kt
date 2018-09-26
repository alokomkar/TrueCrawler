package com.alokomkar.truecrawller.ui

import android.arch.lifecycle.Observer
import com.alokomkar.truecrawller.data.CharacterRequest
import com.alokomkar.truecrawller.data.RequestRepository
import com.alokomkar.truecrawller.data.RequestType
import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.collect.Lists
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.*

class RequestViewModelTest {

    private val characterRequestList = Lists.newArrayList(
            CharacterRequest(RequestType.TenthCharacter, "https://truecaller.com", "10th Word : Second", ""),
            CharacterRequest(RequestType.EveryTenthCharacter, "https://truecaller.com", "10th Word : Second\n20th Word : Second\n30th Word : Second\n", ""),
            CharacterRequest(RequestType.WordCounter, "https://truecaller.com", "All Word Count :\nSecond : 3", "")
    )

    /**
     * To bypass the main thread check
     * **/
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: RequestRepository

    private lateinit var viewModel: RequestViewModel

    /**
     * [ArgumentCaptor] is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private val loadRequestListObserver: ArgumentCaptor<Observer<List<CharacterRequest>>>? = null

    @Before
    fun setupRequestViewModel() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        // Get a reference to the class under test
        viewModel = RequestViewModel( repository )
    }

    @Test
    fun loadRequestListFromRepositoryAndShowInView() {
        // Use the initialized viewModel to fetch request data
        viewModel.fetchLiveData()

        Mockito.verify(repository)
                .fetchLiveData()

        loadRequestListObserver!!.value.onChanged( characterRequestList )

    }
}